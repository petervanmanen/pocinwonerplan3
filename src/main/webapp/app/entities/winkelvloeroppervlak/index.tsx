import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Winkelvloeroppervlak from './winkelvloeroppervlak';
import WinkelvloeroppervlakDetail from './winkelvloeroppervlak-detail';
import WinkelvloeroppervlakUpdate from './winkelvloeroppervlak-update';
import WinkelvloeroppervlakDeleteDialog from './winkelvloeroppervlak-delete-dialog';

const WinkelvloeroppervlakRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Winkelvloeroppervlak />} />
    <Route path="new" element={<WinkelvloeroppervlakUpdate />} />
    <Route path=":id">
      <Route index element={<WinkelvloeroppervlakDetail />} />
      <Route path="edit" element={<WinkelvloeroppervlakUpdate />} />
      <Route path="delete" element={<WinkelvloeroppervlakDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WinkelvloeroppervlakRoutes;
