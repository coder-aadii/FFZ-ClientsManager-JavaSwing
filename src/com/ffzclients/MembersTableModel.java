package com.ffzclients;

import javax.swing.table.AbstractTableModel;
import java.sql.Date;
import java.util.List;

public class MembersTableModel extends AbstractTableModel {
    private List<Member> members;
    private String[] columnNames = {"ID", "Name", "Phone", "Email", "Fitness Type", "Fee Package", "Fee Amount", "Fee Paid On", "Next Fee Payment Date"};

    public MembersTableModel(List<Member> members) {
        this.members = members;
    }

    @Override
    public int getRowCount() {
        return members.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Member member = members.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return member.getId();
            case 1:
                return member.getName();
            case 2:
                return member.getPhone();
            case 3:
                return member.getEmail();
            case 4:
                return member.getFitnessType();
            case 5:
                return member.getFeePackage();
            case 6:
                return member.getFeeAmount();
            case 7:
                return member.getFeePaidOn(); // This is a Date object
            case 8:
                return formatDate(member.getNextFeePaymentDate()); // Format the next payment date
            default:
                return null;
        }
    }

    private String formatDate(Date date) {
        if (date != null) {
            return String.format("%1$td/%1$tm/%1$tY", date);
        } else {
            return "";
        }
    }
}
