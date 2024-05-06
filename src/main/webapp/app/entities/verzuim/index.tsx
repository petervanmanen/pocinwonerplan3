import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Verzuim from './verzuim';
import VerzuimDetail from './verzuim-detail';
import VerzuimUpdate from './verzuim-update';
import VerzuimDeleteDialog from './verzuim-delete-dialog';

const VerzuimRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Verzuim />} />
    <Route path="new" element={<VerzuimUpdate />} />
    <Route path=":id">
      <Route index element={<VerzuimDetail />} />
      <Route path="edit" element={<VerzuimUpdate />} />
      <Route path="delete" element={<VerzuimDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VerzuimRoutes;
