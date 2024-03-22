/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.tableModel;

import hr.algebra.model.User;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author wExzEk
 */
public class UserTableModel extends AbstractTableModel{
    private static final String[] COLUMNS
            = {
                "IDUser",
                "Username",
                "Password",
                "Role"
                
            };
    private List<User> users;

    public UserTableModel(List<User> users) {
        this.users = users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
        }
        return super.getColumnClass(columnIndex); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    @Override
    public String getColumnName(int column) {
        return COLUMNS[column]; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return users.get(rowIndex).getId();
            case 1:
                return users.get(rowIndex).getUsername();
            case 2:
                return users.get(rowIndex).getPassword();
            case 3:
                return users.get(rowIndex).getRole();
            default:
                throw new AssertionError();
        }
    }
}
