import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Balieafspraak from './balieafspraak';
import BalieafspraakDetail from './balieafspraak-detail';
import BalieafspraakUpdate from './balieafspraak-update';
import BalieafspraakDeleteDialog from './balieafspraak-delete-dialog';

const BalieafspraakRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Balieafspraak />} />
    <Route path="new" element={<BalieafspraakUpdate />} />
    <Route path=":id">
      <Route index element={<BalieafspraakDetail />} />
      <Route path="edit" element={<BalieafspraakUpdate />} />
      <Route path="delete" element={<BalieafspraakDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BalieafspraakRoutes;
