package org.unesco.jisis.wizards.marc;

import java.awt.Dimension;
import java.io.File;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;
import org.marc4j.Constants;
import org.openide.util.NbBundle;
import org.unesco.jisis.corelib.client.ConnectionInfo;
import org.unesco.jisis.corelib.client.ConnectionPool;

import org.unesco.jisis.corelib.common.Global;
import org.unesco.jisis.corelib.common.IDatabase;
import org.unesco.jisis.gui.DirectoryChooser;
import org.unesco.jisis.gui.LargeComboBoxRenderer;
import org.unesco.jisis.jisisutils.proxy.ClientDatabaseProxy;
import org.unesco.jisis.jisisutils.proxy.MarkedRecords;
import org.unesco.jisis.jisisutils.proxy.SearchResult;

public final class ExpISO2709VisualPanel extends JPanel {

    private ClientDatabaseProxy db_;

    /**
     * Creates new form expVisualPanel1
     */
    public ExpISO2709VisualPanel() {
        ConnectionInfo connectionInfo = ConnectionPool.getDefaultConnectionInfo();
        IDatabase db = connectionInfo.getDefaultDatabase();

        if (db instanceof ClientDatabaseProxy) {
            db_ = (ClientDatabaseProxy) db;
        } else {
            throw new RuntimeException("ExpISO2709VisualPanel: Cannot cast DB to ClientDatabaseProxy");
        }
        initComponents();

        String lastDir = Global.getClientWorkPath();
        Global.prefs_.put("IMPEXP_OUTPUT_DIR", lastDir);

        txtExpDirectory.setText(lastDir);
        cmbSubfieldDelimiter.setModel(new DefaultComboBoxModel(Global.ascii));
        cmbSubfieldDelimiter.setSelectedItem("031 1F   US    (Unit Separator)");
        String[] fstNames = db_.getFstNames();
        String[] cmbModel;
        if (fstNames == null) {
            cmbModel = new String[]{"<none>"};
        } else {
            cmbModel = new String[fstNames.length + 1];
            cmbModel[0] = "<none>";
            System.arraycopy(fstNames, 0, cmbModel, 1, fstNames.length);
        }
        cmbReformattingFST.setModel(new DefaultComboBoxModel(cmbModel));

        cmbSearch.setEnabled(false);
        rdbAllMfn.setEnabled(true);
        rdbMfns.setEnabled(true);
        rdbMarked.setEnabled(true);
        prepareSearchHistory();
        prepareMarkedRecordsHistory();
        prepareHitSortHistory();
        cmbMarked.setEnabled(false);
        cmbSearch.setEnabled(false);
    }

    private void prepareSearchHistory() {

        List<SearchResult> searchResults = db_.getSearchResults();
        String[] searches = {"No Search"};
        if (searchResults != null && searchResults.size() > 0) {

            int n = searchResults.size();
            searches = new String[n];
            for (int i = 0; i < n; i++) {
                searches[i] = searchResults.get(i).toString();
            }
        } else {
            // Disable Search radio button and combo box
            cmbSearch.setEnabled(false);
            rdbSearchResult.setEnabled(false);
        }
        cmbSearch.setModel(new DefaultComboBoxModel(searches));
        cmbSearch.setPrototypeDisplayValue("Short");
        cmbSearch.setRenderer(new LargeComboBoxRenderer(500));

        cmbSearch.setPreferredSize(new Dimension(500, 30));
        cmbSearch.setMaximumSize(new Dimension(500, 30));

    }

    private void prepareMarkedRecordsHistory() {
        List<MarkedRecords> markedRecords = db_.getMarkedRecordsList();
        String[] markedSets = {"No Marked Sets"};
        if (markedRecords != null && !markedRecords.isEmpty()) {

            int n = markedRecords.size();
            markedSets = new String[n];
            for (int i = 0; i < n; i++) {
                markedSets[i] = markedRecords.get(i).toString();
            }
        } else {
            // Disable Search radio button and combo box
            cmbMarked.setEnabled(false);
            rdbMarked.setEnabled(false);
        }
        cmbMarked.setModel(new DefaultComboBoxModel(markedSets));

        cmbMarked.setPrototypeDisplayValue("Short");
        cmbMarked.setRenderer(new LargeComboBoxRenderer(500));

        cmbMarked.setPreferredSize(new Dimension(500, 30));
        cmbMarked.setMaximumSize(new Dimension(500, 30));

    }

    private void prepareHitSortHistory() {

        String dbHitSortFilePath = Global.getClientWorkPath() + File.separator
                + db_.getDbName()
                + Global.HIT_SORT_FILE_EXT;
        File dbHitSortFile_ = new File(dbHitSortFilePath);

        String dbHitSortHxfFilePath = Global.getClientWorkPath() + File.separator
                + db_.getDbName()
                + Global.HIT_SORT_HXF_FILE_EXT;
        File dbHitSortHxfFile_ = new File(dbHitSortHxfFilePath);

        String[] hitSortNames = new String[1];
        if (!dbHitSortFile_.exists()) {
            hitSortNames[0] = "No HitSorts";
            // Disable Hit File radio button and combo box
            cmbHitSortFile.setEnabled(false);
            rdbHitSort.setEnabled(false);

        } else {
            hitSortNames[0] = dbHitSortFilePath;
        }

//      List<HitSortResult> hitSortResults = db_.getHitSortResults();
//      String[] hitSortNames = {"No HitSorts"};
//      if (hitSortResults != null && !hitSortResults.isEmpty()) {
//
//         int n = hitSortResults.size();
//         hitSortNames = new String[n];
//         for (int i = 0; i < n; i++) {
//            hitSortNames[i] = hitSortResults.get(i).toString();
//         }
//      }else {
//         // Disable Search radio button and combo box
//         cmbHitSortFile.setEnabled(false);
//         rdbHitSort.setEnabled(false);
//      }
        cmbHitSortFile.setModel(new DefaultComboBoxModel(hitSortNames));

    }

    @Override
    public String getName() {
        return NbBundle.getMessage(ExpISO2709VisualPanel.class, "MSG_ExpISO2709VisualPanel");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        outputFilePanel = new javax.swing.JPanel();
        expFileName = new javax.swing.JTextField();
        btnBrowse = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtExpDirectory = new javax.swing.JTextField();
        lblOutputDirectory = new javax.swing.JLabel();
        exportMfnPanel = new javax.swing.JPanel();
        rdbHitSort = new javax.swing.JRadioButton();
        rdbSearchResult = new javax.swing.JRadioButton();
        jLabel15 = new javax.swing.JLabel();
        cmbSearch = new javax.swing.JComboBox();
        rdbMarked = new javax.swing.JRadioButton();
        cmbMarked = new javax.swing.JComboBox();
        rdbAllMfn = new javax.swing.JRadioButton();
        rdbMfns = new javax.swing.JRadioButton();
        jLabel14 = new javax.swing.JLabel();
        rdbMfnRange = new javax.swing.JRadioButton();
        txtMfns = new javax.swing.JTextField();
        cmbHitSortFile = new javax.swing.JComboBox();
        optionsPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        cmbReformattingFST = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        NumberFormat nf = NumberFormat.getIntegerInstance();
        txtOutputLineLength = new javax.swing.JFormattedTextField(nf);
        jLabel7 = new javax.swing.JLabel();
        txtOutputTagMFN = new javax.swing.JFormattedTextField(nf);
        jLabel8 = new javax.swing.JLabel();
        cmbEncoding = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        txtRenumberFromMFN = new javax.swing.JFormattedTextField(nf);
        jPanel2 = new javax.swing.JPanel();
        JFormattedTextField.AbstractFormatter hexFormat = null;
        try {
            hexFormat = new MaskFormatter("HH");
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        String hexRT = Integer.toHexString(Constants.RT);
        txtRecordTerminator = new javax.swing.JFormattedTextField(hexFormat);
        jLabel11 = new javax.swing.JLabel();
        String hex = Integer.toHexString(Constants.FT);
        txtFieldTerminator = new javax.swing.JFormattedTextField(hexFormat);
        cmbSubfieldDelimiter = new javax.swing.JComboBox();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnResetDefaultTerminators = new javax.swing.JButton();

        setMaximumSize(new java.awt.Dimension(1000, 32767));
        setPreferredSize(new java.awt.Dimension(800, 531));

        outputFilePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Output ISO File"));

        org.openide.awt.Mnemonics.setLocalizedText(btnBrowse, NbBundle.getMessage(ExpISO2709VisualPanel.class, "btnBrowse")); // NOI18N
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel2, "Name of Output ISO File:");

        txtExpDirectory.setEditable(false);

        lblOutputDirectory.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(lblOutputDirectory, "Output Directory:");

        javax.swing.GroupLayout outputFilePanelLayout = new javax.swing.GroupLayout(outputFilePanel);
        outputFilePanel.setLayout(outputFilePanelLayout);
        outputFilePanelLayout.setHorizontalGroup(
            outputFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputFilePanelLayout.createSequentialGroup()
                .addGroup(outputFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblOutputDirectory)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(outputFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(outputFilePanelLayout.createSequentialGroup()
                        .addComponent(txtExpDirectory, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBrowse))
                    .addComponent(expFileName, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        outputFilePanelLayout.setVerticalGroup(
            outputFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(outputFilePanelLayout.createSequentialGroup()
                .addGroup(outputFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(expFileName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(outputFilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtExpDirectory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblOutputDirectory)
                    .addComponent(btnBrowse))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        exportMfnPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Export:"));

        buttonGroup1.add(rdbHitSort);
        org.openide.awt.Mnemonics.setLocalizedText(rdbHitSort, "Use this Hit Sort File for driving the output:");
        rdbHitSort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbHitSortActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdbSearchResult);
        rdbSearchResult.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(rdbSearchResult, org.openide.util.NbBundle.getMessage(ExpISO2709VisualPanel.class, "ExpMarcXmlVisualPanel.rdbSearchResult.text")); // NOI18N
        rdbSearchResult.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbSearchResultActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel15, org.openide.util.NbBundle.getMessage(ExpISO2709VisualPanel.class, "ExpMarcXmlVisualPanel.jLabel15.text")); // NOI18N

        cmbSearch.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        buttonGroup2.add(rdbMarked);
        org.openide.awt.Mnemonics.setLocalizedText(rdbMarked, org.openide.util.NbBundle.getMessage(ExpISO2709VisualPanel.class, "ExpMarcXmlVisualPanel.rdbMarked.text")); // NOI18N
        rdbMarked.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbMarkedActionPerformed(evt);
            }
        });

        cmbMarked.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        buttonGroup2.add(rdbAllMfn);
        rdbAllMfn.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(rdbAllMfn, org.openide.util.NbBundle.getMessage(ExpISO2709VisualPanel.class, "ExpMarcXmlVisualPanel.rdbAllMfn.text")); // NOI18N
        rdbAllMfn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbAllMfnActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdbMfns);
        org.openide.awt.Mnemonics.setLocalizedText(rdbMfns, org.openide.util.NbBundle.getMessage(ExpISO2709VisualPanel.class, "ExpMarcXmlVisualPanel.rdbMfns.text")); // NOI18N
        rdbMfns.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbMfnsActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel14, org.openide.util.NbBundle.getMessage(ExpISO2709VisualPanel.class, "ExpMarcXmlVisualPanel.jLabel14.text")); // NOI18N

        buttonGroup1.add(rdbMfnRange);
        rdbMfnRange.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        rdbMfnRange.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(rdbMfnRange, org.openide.util.NbBundle.getMessage(ExpISO2709VisualPanel.class, "ExpMarcXmlVisualPanel.rdbMfnRange.text")); // NOI18N
        rdbMfnRange.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdbMfnRangeActionPerformed(evt);
            }
        });

        txtMfns.setText(org.openide.util.NbBundle.getMessage(ExpISO2709VisualPanel.class, "ExpMarcXmlVisualPanel.txtMfns.text")); // NOI18N
        txtMfns.setEnabled(false);

        cmbHitSortFile.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout exportMfnPanelLayout = new javax.swing.GroupLayout(exportMfnPanel);
        exportMfnPanel.setLayout(exportMfnPanelLayout);
        exportMfnPanelLayout.setHorizontalGroup(
            exportMfnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exportMfnPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(exportMfnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(exportMfnPanelLayout.createSequentialGroup()
                        .addComponent(rdbHitSort)
                        .addGap(18, 18, 18)
                        .addComponent(cmbHitSortFile, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(exportMfnPanelLayout.createSequentialGroup()
                        .addComponent(rdbSearchResult)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel15))
                    .addGroup(exportMfnPanelLayout.createSequentialGroup()
                        .addGap(249, 249, 249)
                        .addGroup(exportMfnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbMarked, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbSearch, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel14)
                            .addComponent(txtMfns)))
                    .addComponent(rdbMfnRange)
                    .addGroup(exportMfnPanelLayout.createSequentialGroup()
                        .addGap(119, 119, 119)
                        .addGroup(exportMfnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(exportMfnPanelLayout.createSequentialGroup()
                                .addGap(57, 57, 57)
                                .addComponent(rdbMfns))
                            .addComponent(rdbAllMfn)
                            .addComponent(rdbMarked))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        exportMfnPanelLayout.setVerticalGroup(
            exportMfnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exportMfnPanelLayout.createSequentialGroup()
                .addGroup(exportMfnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(exportMfnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rdbMfnRange)
                        .addComponent(rdbAllMfn)
                        .addComponent(rdbMfns))
                    .addGroup(exportMfnPanelLayout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtMfns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10)
                .addGroup(exportMfnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdbMarked)
                    .addComponent(cmbMarked, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(exportMfnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdbSearchResult)
                    .addComponent(jLabel15)
                    .addComponent(cmbSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(exportMfnPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rdbHitSort)
                    .addComponent(cmbHitSortFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        optionsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Options"));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel5, "Reformatting FST:");

        cmbReformattingFST.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbReformattingFST.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbReformattingFSTActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(jLabel12, "(Use zero for no limit)");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel4, "Output Line Length:");

        txtOutputLineLength.setValue(80);
        txtOutputLineLength.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtOutputLineLength.setText("80");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel7, "Output Tag Containing MFN:");

        txtOutputTagMFN.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel8, "Encoding:");

        cmbEncoding.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "US-ASCII", "CP850 (or IBM850)", "ISO-8859-1", "UTF-8", "UTF-16", "UTF-16BE", "UTF-16LE", "CP1256 (Arabic Windows-1256)", "MARC-8", "ISO-5426 (Used by UNIMARC)", "ISO-6937 (Used by UNIMARC)", " ", " ", " " }));
        cmbEncoding.setSelectedIndex(3);
        cmbEncoding.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEncodingActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel6, "Renumber Records From MFN:");

        txtRenumberFromMFN.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Separators"));
        jPanel2.setMaximumSize(new java.awt.Dimension(100, 32767));

        txtRecordTerminator.setText(hexRT);
        txtRecordTerminator.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel11, "Subfield Delimiter");

        txtFieldTerminator.setText(hex);
        txtFieldTerminator.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        cmbSubfieldDelimiter.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel10, "Record Terminator");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(jLabel9, "Field Terminator");

        org.openide.awt.Mnemonics.setLocalizedText(btnResetDefaultTerminators, "Reset Defaults");
        btnResetDefaultTerminators.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetDefaultTerminatorsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtFieldTerminator)
                    .addComponent(txtRecordTerminator)
                    .addComponent(cmbSubfieldDelimiter, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnResetDefaultTerminators)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFieldTerminator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRecordTerminator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbSubfieldDelimiter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnResetDefaultTerminators)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout optionsPanelLayout = new javax.swing.GroupLayout(optionsPanel);
        optionsPanel.setLayout(optionsPanelLayout);
        optionsPanelLayout.setHorizontalGroup(
            optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, optionsPanelLayout.createSequentialGroup()
                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(optionsPanelLayout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(optionsPanelLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtOutputLineLength, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(optionsPanelLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbReformattingFST, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtOutputTagMFN, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(txtRenumberFromMFN, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 77, Short.MAX_VALUE)))))
                        .addGap(24, 24, 24))
                    .addGroup(optionsPanelLayout.createSequentialGroup()
                        .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(optionsPanelLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6)))
                            .addGroup(optionsPanelLayout.createSequentialGroup()
                                .addGap(188, 188, 188)
                                .addComponent(jLabel12)))
                        .addGap(50, 50, 50)))
                .addGap(6, 6, 6)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(optionsPanelLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(cmbEncoding, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(56, 56, 56))
        );
        optionsPanelLayout.setVerticalGroup(
            optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(optionsPanelLayout.createSequentialGroup()
                .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(optionsPanelLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(txtOutputLineLength, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(cmbReformattingFST, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtRenumberFromMFN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtOutputTagMFN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(optionsPanelLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(optionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbEncoding, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(optionsPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(outputFilePanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(exportMfnPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(outputFilePanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(exportMfnPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(optionsPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseActionPerformed

        selectDirectory();

    }//GEN-LAST:event_btnBrowseActionPerformed

    private void cmbEncodingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEncodingActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_cmbEncodingActionPerformed

    private void btnResetDefaultTerminatorsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetDefaultTerminatorsActionPerformed
        resetDefaultTerminators();
}//GEN-LAST:event_btnResetDefaultTerminatorsActionPerformed

    private void cmbReformattingFSTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbReformattingFSTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbReformattingFSTActionPerformed

    private void rdbMfnsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbMfnsActionPerformed
        cmbMarked.setEnabled(false);
        txtMfns.setEnabled(true);
}//GEN-LAST:event_rdbMfnsActionPerformed

    private void rdbAllMfnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbAllMfnActionPerformed
        cmbMarked.setEnabled(false);
        txtMfns.setEnabled(false);

}//GEN-LAST:event_rdbAllMfnActionPerformed

    private void rdbSearchResultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbSearchResultActionPerformed
        cmbSearch.setEnabled(true);
        cmbMarked.setEnabled(false);
        cmbHitSortFile.setEnabled(false);

        // Deselect All/Mfns/Marked
        rdbAllMfn.setSelected(false);
        rdbMfns.setSelected(false);
        rdbMarked.setSelected(false);
        txtMfns.setEnabled(false);
        rdbAllMfn.setEnabled(false);
        rdbMfns.setEnabled(false);
        rdbMarked.setEnabled(false);
}//GEN-LAST:event_rdbSearchResultActionPerformed

    private void rdbHitSortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbHitSortActionPerformed
        cmbHitSortFile.setEnabled(true);
        cmbSearch.setEnabled(false);
        cmbMarked.setEnabled(false);

        // Deselect All/Mfns/Marked
        rdbAllMfn.setSelected(false);
        rdbMfns.setSelected(false);
        rdbMarked.setSelected(false);
        txtMfns.setEnabled(false);
    }//GEN-LAST:event_rdbHitSortActionPerformed

    private void rdbMarkedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbMarkedActionPerformed
        cmbMarked.setEnabled(true);
        cmbSearch.setEnabled(false);
        cmbHitSortFile.setEnabled(false);

        txtMfns.setEnabled(false);
}//GEN-LAST:event_rdbMarkedActionPerformed

    private void rdbMfnRangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdbMfnRangeActionPerformed

        rdbAllMfn.setEnabled(true);
        rdbMfns.setEnabled(true);
        if (!cmbMarked.getItemAt(0).equals("No Marked Sets")) {
            rdbMarked.setEnabled(true);
        }
        rdbAllMfn.setSelected(true);
        rdbMfns.setSelected(false);
        rdbMarked.setSelected(false);
        txtMfns.setEnabled(false);

        cmbSearch.setEnabled(false);
        cmbHitSortFile.setEnabled(false);
}//GEN-LAST:event_rdbMfnRangeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrowse;
    private javax.swing.JButton btnResetDefaultTerminators;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox cmbEncoding;
    private javax.swing.JComboBox cmbHitSortFile;
    private javax.swing.JComboBox cmbMarked;
    private javax.swing.JComboBox cmbReformattingFST;
    private javax.swing.JComboBox cmbSearch;
    private javax.swing.JComboBox cmbSubfieldDelimiter;
    private javax.swing.JTextField expFileName;
    private javax.swing.JPanel exportMfnPanel;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblOutputDirectory;
    private javax.swing.JPanel optionsPanel;
    private javax.swing.JPanel outputFilePanel;
    private javax.swing.JRadioButton rdbAllMfn;
    private javax.swing.JRadioButton rdbHitSort;
    private javax.swing.JRadioButton rdbMarked;
    private javax.swing.JRadioButton rdbMfnRange;
    private javax.swing.JRadioButton rdbMfns;
    private javax.swing.JRadioButton rdbSearchResult;
    private javax.swing.JTextField txtExpDirectory;
    private javax.swing.JFormattedTextField txtFieldTerminator;
    private javax.swing.JTextField txtMfns;
    private javax.swing.JFormattedTextField txtOutputLineLength;
    private javax.swing.JFormattedTextField txtOutputTagMFN;
    private javax.swing.JFormattedTextField txtRecordTerminator;
    private javax.swing.JFormattedTextField txtRenumberFromMFN;
    // End of variables declaration//GEN-END:variables
    public String getSelectedFile() {
        String fileName = expFileName.getText();
        if (!fileName.toLowerCase().endsWith(".iso")) {
            fileName += ".iso";
        }
        String fullFileName = Global.prefs_.get("IMPEXP_OUTPUT_DIR", "")
                + File.separator + fileName;

        return fullFileName;
    }

    private String selectDirectory() {

        //prefs = Preferences.userNodeForPackage(this.getClass());
        String lastDir = Global.getClientWorkPath();
        DirectoryChooser dc = new DirectoryChooser(new File(lastDir));
        dc.showOpenDialog(this);
        File file;
        if ((file = dc.getSelectedFile()) != null) {
            Global.prefs_.put("IMPEXP_OUTPUT_DIR", file.getAbsolutePath());
            txtExpDirectory.setText(file.getAbsolutePath());
            return file.getAbsolutePath();
        }
        return "";

    }

    public int getOutputLineLength() {
        Number num = (Number) txtOutputLineLength.getValue();
        return (num == null) ? -1 : num.intValue();
    }

    public int getSearchHistoryIndex() {
        int index = -1;
        if (rdbSearchResult.isSelected()) {
            index = cmbSearch.getSelectedIndex();
        }
        return index;
    }

    public int getMarkedRecordsIndex() {
        int index = -1;
        if (rdbMarked.isSelected()) {
            index = cmbMarked.getSelectedIndex();
        }

        return index;
    }

    public String getReformattingFST() {
        int index = cmbReformattingFST.getSelectedIndex();
        return (String) ((index == -1) ? "<none>" : cmbReformattingFST.getSelectedItem());
    }

    public String getEncoding() {
        String encoding = (String) cmbEncoding.getSelectedItem();
        if (encoding.startsWith("CP850")) {
            encoding = "IBM850";
        } else if (encoding.startsWith("CP864")) {
            encoding = "IBM864";
        } else if (encoding.startsWith("CP1256")) {
            encoding = " windows-1256";
        } else if (encoding.startsWith("MARC-8")) {
            encoding = "MARC-8";
        } else if (encoding.startsWith("ISO-5426")) {
            encoding = "ISO5426";
        } else if (encoding.startsWith("ISO-6937")) {
            encoding = "ISO6937";
        }
        return encoding;

    }

    public String geHitSortFile() {
        int index = cmbHitSortFile.getSelectedIndex();
        return (String) ((index == -1) ? "" : cmbHitSortFile.getSelectedItem());
    }

    public int getRenumberFromMFN() {
        Number num = (Number) txtRenumberFromMFN.getValue();
        return (num == null) ? -1 : num.intValue();
    }

    public int getOutputTagMFN() {
        Number num = (Number) txtOutputTagMFN.getValue();
        return (num == null) ? -1 : num.intValue();
    }

    public int getFieldTerminator() {
        int FT = Integer.parseInt(txtFieldTerminator.getText(), 16);
        return (FT == Constants.FT) ? -1 : FT;
    }

    public int getRecordTerminator() {
        int RT = Integer.parseInt(txtRecordTerminator.getText(), 16);
        return (RT == Constants.RT) ? -1 : RT;
    }

    public int getSubfieldDelimiter() {
        int subfieldDelimiter = (int) ('^'); // Default ISIS caret circomflex
        String s = (String) cmbSubfieldDelimiter.getSelectedItem();
        try {
            subfieldDelimiter = Integer.parseInt(s.substring(0, 3));
        } catch (NumberFormatException ex) {
            // Do nothing
            System.out.println("Error converting Input subfield delimiter:" + s);
        }

        return subfieldDelimiter;
    }

    public int getMfnsRangeOption() {
        if (rdbMfnRange.isSelected() && rdbAllMfn.isSelected()) {
            return Global.MFNS_OPTION_ALL;
        } else if (rdbMfnRange.isSelected() && rdbMfns.isSelected()) {
            return Global.MFNS_OPTION_RANGE;
        } else if (rdbMfnRange.isSelected() && rdbMarked.isSelected()) {
            return Global.MFNS_OPTION_MARKED;
        } else if (rdbSearchResult.isSelected()) {
            return Global.MFNS_OPTION_SEARCH;
        } else if (rdbHitSort.isSelected()) {
            return Global.MFNS_OPTION_HITSORT;
        } else {
            return Global.MFNS_OPTION_ALL;
        }
    }

    public String getMfnRanges() {
        String s = txtMfns.getText();
        return s;
    }

    public boolean isSearchResult() {
        return rdbSearchResult.isSelected();
    }

    private void resetDefaultTerminators() {
        txtFieldTerminator.setText(Integer.toHexString(Constants.FT));
        txtRecordTerminator.setText(Integer.toHexString(Constants.RT));
        cmbSubfieldDelimiter.setSelectedItem("031 1F   US    (Unit Separator)");

    }

}
