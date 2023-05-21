// Sample data for demonstration
const attendanceData = [
    { employeeId: 1, date: '2023-05-20', status: 'Present' },
    { employeeId: 2, date: '2023-05-20', status: 'Absent' },
    { employeeId: 1, date: '2023-05-19', status: 'Present' },
    { employeeId: 2, date: '2023-05-19', status: 'Present' },
  ];
  
  const leaveBalanceData = [
    { employeeId: 1, leaveType: 'Annual', balance: 10 },
    { employeeId: 2, leaveType: 'Annual', balance: 12 },
    { employeeId: 1, leaveType: 'Sick', balance: 5 },
    { employeeId: 2, leaveType: 'Sick', balance: 8 },
  ];
  
  // Populate attendance table
  const attendanceTable = document.getElementById('attendanceTable');
  attendanceData.forEach((record) => {
    const row = attendanceTable.insertRow();
    row.insertCell().textContent = record.employeeId;
    row.insertCell().textContent = record.date;
    row.insertCell().textContent = record.status;
  });
  
  // Populate leave balance table
  const balanceTable = document.getElementById('balanceTable');
  leaveBalanceData.forEach((balance) => {
    const row = balanceTable.insertRow();
    row.insertCell().textContent = balance.employeeId;
    row.insertCell().textContent = balance.leaveType;
    row.insertCell().textContent = balance.balance;
  });
  