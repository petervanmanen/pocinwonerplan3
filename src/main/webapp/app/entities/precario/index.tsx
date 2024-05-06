import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Precario from './precario';
import PrecarioDetail from './precario-detail';
import PrecarioUpdate from './precario-update';
import PrecarioDeleteDialog from './precario-delete-dialog';

const PrecarioRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Precario />} />
    <Route path="new" element={<PrecarioUpdate />} />
    <Route path=":id">
      <Route index element={<PrecarioDetail />} />
      <Route path="edit" element={<PrecarioUpdate />} />
      <Route path="delete" element={<PrecarioDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PrecarioRoutes;
