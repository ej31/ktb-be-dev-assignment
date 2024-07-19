import React, { useState, useEffect } from 'react';
import axios from 'axios';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import Alert from '@mui/material/Alert';
import { DataGrid } from '@mui/x-data-grid';
import { DatePicker } from '@mui/x-date-pickers';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers';
import Autocomplete from '@mui/material/Autocomplete';

const StockHistory = () => {
  const [companyCode, setCompanyCode] = useState('');
  const [companies, setCompanies] = useState([]);
  const [startDate, setStartDate] = useState(new Date());
  const [endDate, setEndDate] = useState(new Date());
  const [stockData, setStockData] = useState([]);
  const [error, setError] = useState(null);

  // 하드코딩된 API 키
  const apikey = 'c18aa07f-f005-4c2f-b6db-dff8294e6b5e';

  useEffect(() => {
    const fetchCompanies = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/v1/companies', {
          params: { apikey }
        });
        setCompanies(response.data);
      } catch (error) {
        setError(error.response ? error.response.data : 'An error occurred while fetching companies');
      }
    };

    fetchCompanies();
  }, []);

  const fetchStockHistory = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/v1/stocks/history', {
        params: {
          companyCode,
          startDate: startDate.toISOString().split('T')[0],
          endDate: endDate.toISOString().split('T')[0],
          apikey
        }
      });
      setStockData(response.data);
      setError(null);
    } catch (error) {
      setError(error.response ? error.response.data : 'An error occurred while fetching stock history');
    }
  };

  const columns = [
    { field: 'id', headerName: 'ID', width: 90 },
    { field: 'tradeDate', headerName: 'Trade Date', width: 150 },
    { field: 'closePrice', headerName: 'Close Price', width: 150 }
  ];

  return (
    <Box>
      <h1>Stock History</h1>
      <Autocomplete
        options={companies}
        getOptionLabel={(option) => option.companyName}
        onChange={(event, newValue) => {
          setCompanyCode(newValue ? newValue.companyCode : '');
        }}
        renderInput={(params) => <TextField {...params} label="Company Code" />}
      />
      <LocalizationProvider dateAdapter={AdapterDayjs}>
        <DatePicker
          label="Start Date"
          value={startDate}
          onChange={(newValue) => setStartDate(newValue)}
          renderInput={(params) => <TextField {...params} />}
        />
        <DatePicker
          label="End Date"
          value={endDate}
          onChange={(newValue) => setEndDate(newValue)}
          renderInput={(params) => <TextField {...params} />}
        />
      </LocalizationProvider>
      <Button variant="contained" onClick={fetchStockHistory}>
        Fetch Stock History
      </Button>
      {error && (
        <Alert severity="error">
          {typeof error === 'string' ? error : JSON.stringify(error)}
        </Alert>
      )}
      <div style={{ height: 400, width: '100%' }}>
        <DataGrid rows={stockData} columns={columns} />
      </div>
    </Box>
  );
};

export default StockHistory;
