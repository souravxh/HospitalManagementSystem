package ui;

import dao.PatientDAO;
import model.Patient;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;


public class PatientPanel extends JPanel {


    private JTextField nameField;
    private JTextField ageField;
    private JTextField phoneField;
    private JTextField addressField;
    private JTextField bloodField;
    private JTextField diseaseField;

    private JComboBox<String> genderBox;

    private JTable table;

    private DefaultTableModel model;

    private JTextField searchField;

    private PatientDAO dao;



    public PatientPanel(){


        dao = new PatientDAO();

        setLayout(new BorderLayout());

        setBackground(
                new Color(240,245,250)
        );


        createUI();

        loadPatients();

    }





    private void createUI(){


        // HEADER

        JPanel header =
                new JPanel(new BorderLayout());


        header.setBackground(
                new Color(240,245,250)
        );


        JLabel title =
                new JLabel(
                        "Patient Management"
                );


        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        32
                )
        );


        title.setBorder(
                new EmptyBorder(
                        20,20,20,20
                )
        );


        header.add(
                title,
                BorderLayout.WEST
        );



        searchField =
                new JTextField();


        searchField.setPreferredSize(
                new Dimension(250,35)
        );


        JButton searchBtn =
                new JButton("Search");


        JPanel searchPanel =
                new JPanel();


        searchPanel.add(searchField);

        searchPanel.add(searchBtn);


        header.add(
                searchPanel,
                BorderLayout.EAST
        );


        add(
                header,
                BorderLayout.NORTH
        );





        // FORM


        JPanel form =
                new JPanel(
                        new GridLayout(
                                4,
                                4,
                                15,
                                15
                        )
                );


        form.setBackground(Color.WHITE);


        form.setBorder(
                new EmptyBorder(
                        20,20,20,20
                )
        );



        nameField = new JTextField();

        ageField = new JTextField();

        phoneField = new JTextField();

        addressField = new JTextField();

        bloodField = new JTextField();

        diseaseField = new JTextField();



        genderBox =
                new JComboBox<>(
                        new String[]{
                                "Male",
                                "Female",
                                "Other"
                        }
                );




        form.add(new JLabel("Name"));
        form.add(nameField);


        form.add(new JLabel("Age"));
        form.add(ageField);


        form.add(new JLabel("Gender"));
        form.add(genderBox);


        form.add(new JLabel("Phone"));
        form.add(phoneField);


        form.add(new JLabel("Address"));
        form.add(addressField);


        form.add(new JLabel("Blood Group"));
        form.add(bloodField);


        form.add(new JLabel("Disease"));
        form.add(diseaseField);



        JButton addBtn =
                new JButton("Add Patient");


        JButton updateBtn =
                new JButton("Update");


        JButton deleteBtn =
                new JButton("Delete");



        form.add(addBtn);

        form.add(updateBtn);

        form.add(deleteBtn);





        // TABLE


        model =
                new DefaultTableModel();



        model.setColumnIdentifiers(
                new String[]{
                        "ID",
                        "Name",
                        "Age",
                        "Gender",
                        "Phone",
                        "Address",
                        "Blood",
                        "Disease"
                }
        );



        table =
                new JTable(model);


        table.setRowHeight(30);


        table.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        15
                )
        );



        JScrollPane scroll =
                new JScrollPane(table);



        JPanel center =
                new JPanel(
                        new BorderLayout()
                );


        center.setBackground(
                new Color(240,245,250)
        );


        center.setBorder(
                new EmptyBorder(
                        10,20,20,20
                )
        );


        center.add(
                form,
                BorderLayout.NORTH
        );


        center.add(
                scroll,
                BorderLayout.CENTER
        );



        add(
                center,
                BorderLayout.CENTER
        );





        // ADD BUTTON


        addBtn.addActionListener(e -> {


            Patient p =
                    getPatientFromForm();



            if(p == null)
                return;



            if(dao.addPatient(p)){


                JOptionPane.showMessageDialog(
                        this,
                        "Patient Added Successfully"
                );


                loadPatients();

                clearForm();

            }

        });






        // UPDATE BUTTON


        updateBtn.addActionListener(e -> {



            int row =
                    table.getSelectedRow();



            if(row == -1){


                JOptionPane.showMessageDialog(
                        this,
                        "Select patient first"
                );


                return;

            }



            Patient p =
                    getPatientFromForm();



            if(p == null)
                return;



            p.setId(
                    Integer.parseInt(
                            model.getValueAt(row,0)
                                    .toString()
                    )
            );



            if(dao.updatePatient(p)){


                JOptionPane.showMessageDialog(
                        this,
                        "Updated Successfully"
                );


                loadPatients();

                clearForm();

            }



        });







        // DELETE BUTTON


        deleteBtn.addActionListener(e -> {



            int row =
                    table.getSelectedRow();



            if(row!=-1){


                int id =
                        Integer.parseInt(
                                model.getValueAt(row,0)
                                        .toString()
                        );



                dao.deletePatient(id);


                loadPatients();


                clearForm();

            }


        });







        // SEARCH


        searchBtn.addActionListener(e ->{


            String text =
                    searchField.getText();



            if(text.isEmpty()){

                loadPatients();

            }
            else{

                loadTable(
                        dao.searchPatient(text)
                );

            }


        });







        // TABLE SELECT


        table.getSelectionModel()
                .addListSelectionListener(e ->{


                    int row =
                            table.getSelectedRow();



                    if(row!=-1){


                        nameField.setText(
                                model.getValueAt(row,1)
                                        .toString()
                        );



                        ageField.setText(
                                model.getValueAt(row,2)
                                        .toString()
                        );



                        genderBox.setSelectedItem(
                                model.getValueAt(row,3)
                                        .toString()
                        );



                        phoneField.setText(
                                model.getValueAt(row,4)
                                        .toString()
                        );



                        addressField.setText(
                                model.getValueAt(row,5)
                                        .toString()
                        );



                        bloodField.setText(
                                model.getValueAt(row,6)
                                        .toString()
                        );



                        diseaseField.setText(
                                model.getValueAt(row,7)
                                        .toString()
                        );


                    }


                });


    }







    private Patient getPatientFromForm(){


        Patient p =
                new Patient();



        p.setName(
                nameField.getText()
        );



        try{


            p.setAge(
                    Integer.parseInt(
                            ageField.getText()
                    )
            );


        }
        catch(Exception e){


            JOptionPane.showMessageDialog(
                    this,
                    "Age must be numeric"
            );


            return null;

        }





        p.setGender(
                genderBox.getSelectedItem()
                        .toString()
        );



        p.setPhone(
                phoneField.getText()
        );


        p.setAddress(
                addressField.getText()
        );


        p.setBloodGroup(
                bloodField.getText()
        );


        p.setDisease(
                diseaseField.getText()
        );



        return p;


    }







    private void loadPatients(){


        loadTable(
                dao.getAllPatients()
        );


    }






    private void loadTable(
            List<Patient> patients
    ){


        model.setRowCount(0);



        for(Patient p:patients){



            model.addRow(
                    new Object[]{

                            p.getId(),

                            p.getName(),

                            p.getAge(),

                            p.getGender(),

                            p.getPhone(),

                            p.getAddress(),

                            p.getBloodGroup(),

                            p.getDisease()

                    }
            );


        }


    }







    private void clearForm(){


        nameField.setText("");

        ageField.setText("");

        phoneField.setText("");

        addressField.setText("");

        bloodField.setText("");

        diseaseField.setText("");

        genderBox.setSelectedIndex(0);


    }



}