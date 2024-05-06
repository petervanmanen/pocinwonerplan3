import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Prijsafspraak from './prijsafspraak';
import PrijsafspraakDetail from './prijsafspraak-detail';
import PrijsafspraakUpdate from './prijsafspraak-update';
import PrijsafspraakDeleteDialog from './prijsafspraak-delete-dialog';

const PrijsafspraakRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Prijsafspraak />} />
    <Route path="new" element={<PrijsafspraakUpdate />} />
    <Route path=":id">
      <Route index element={<PrijsafspraakDetail />} />
      <Route path="edit" element={<PrijsafspraakUpdate />} />
      <Route path="delete" element={<PrijsafspraakDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PrijsafspraakRoutes;
