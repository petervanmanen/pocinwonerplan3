import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Prijsregel from './prijsregel';
import PrijsregelDetail from './prijsregel-detail';
import PrijsregelUpdate from './prijsregel-update';
import PrijsregelDeleteDialog from './prijsregel-delete-dialog';

const PrijsregelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Prijsregel />} />
    <Route path="new" element={<PrijsregelUpdate />} />
    <Route path=":id">
      <Route index element={<PrijsregelDetail />} />
      <Route path="edit" element={<PrijsregelUpdate />} />
      <Route path="delete" element={<PrijsregelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PrijsregelRoutes;
