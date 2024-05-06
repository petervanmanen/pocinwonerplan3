import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Opbreking from './opbreking';
import OpbrekingDetail from './opbreking-detail';
import OpbrekingUpdate from './opbreking-update';
import OpbrekingDeleteDialog from './opbreking-delete-dialog';

const OpbrekingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Opbreking />} />
    <Route path="new" element={<OpbrekingUpdate />} />
    <Route path=":id">
      <Route index element={<OpbrekingDetail />} />
      <Route path="edit" element={<OpbrekingUpdate />} />
      <Route path="delete" element={<OpbrekingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OpbrekingRoutes;
