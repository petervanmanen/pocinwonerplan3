import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Brondocumenten from './brondocumenten';
import BrondocumentenDetail from './brondocumenten-detail';
import BrondocumentenUpdate from './brondocumenten-update';
import BrondocumentenDeleteDialog from './brondocumenten-delete-dialog';

const BrondocumentenRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Brondocumenten />} />
    <Route path="new" element={<BrondocumentenUpdate />} />
    <Route path=":id">
      <Route index element={<BrondocumentenDetail />} />
      <Route path="edit" element={<BrondocumentenUpdate />} />
      <Route path="delete" element={<BrondocumentenDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BrondocumentenRoutes;
