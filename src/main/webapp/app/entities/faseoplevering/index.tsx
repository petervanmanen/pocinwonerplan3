import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Faseoplevering from './faseoplevering';
import FaseopleveringDetail from './faseoplevering-detail';
import FaseopleveringUpdate from './faseoplevering-update';
import FaseopleveringDeleteDialog from './faseoplevering-delete-dialog';

const FaseopleveringRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Faseoplevering />} />
    <Route path="new" element={<FaseopleveringUpdate />} />
    <Route path=":id">
      <Route index element={<FaseopleveringDetail />} />
      <Route path="edit" element={<FaseopleveringUpdate />} />
      <Route path="delete" element={<FaseopleveringDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FaseopleveringRoutes;
