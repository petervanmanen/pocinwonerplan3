import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Factuurregel from './factuurregel';
import FactuurregelDetail from './factuurregel-detail';
import FactuurregelUpdate from './factuurregel-update';
import FactuurregelDeleteDialog from './factuurregel-delete-dialog';

const FactuurregelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Factuurregel />} />
    <Route path="new" element={<FactuurregelUpdate />} />
    <Route path=":id">
      <Route index element={<FactuurregelDetail />} />
      <Route path="edit" element={<FactuurregelUpdate />} />
      <Route path="delete" element={<FactuurregelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FactuurregelRoutes;
