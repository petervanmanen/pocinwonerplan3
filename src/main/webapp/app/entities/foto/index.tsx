import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Foto from './foto';
import FotoDetail from './foto-detail';
import FotoUpdate from './foto-update';
import FotoDeleteDialog from './foto-delete-dialog';

const FotoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Foto />} />
    <Route path="new" element={<FotoUpdate />} />
    <Route path=":id">
      <Route index element={<FotoDetail />} />
      <Route path="edit" element={<FotoUpdate />} />
      <Route path="delete" element={<FotoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FotoRoutes;
