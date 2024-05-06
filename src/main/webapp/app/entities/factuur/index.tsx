import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Factuur from './factuur';
import FactuurDetail from './factuur-detail';
import FactuurUpdate from './factuur-update';
import FactuurDeleteDialog from './factuur-delete-dialog';

const FactuurRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Factuur />} />
    <Route path="new" element={<FactuurUpdate />} />
    <Route path=":id">
      <Route index element={<FactuurDetail />} />
      <Route path="edit" element={<FactuurUpdate />} />
      <Route path="delete" element={<FactuurDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FactuurRoutes;
