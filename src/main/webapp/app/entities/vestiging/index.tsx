import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Vestiging from './vestiging';
import VestigingDetail from './vestiging-detail';
import VestigingUpdate from './vestiging-update';
import VestigingDeleteDialog from './vestiging-delete-dialog';

const VestigingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Vestiging />} />
    <Route path="new" element={<VestigingUpdate />} />
    <Route path=":id">
      <Route index element={<VestigingDetail />} />
      <Route path="edit" element={<VestigingUpdate />} />
      <Route path="delete" element={<VestigingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VestigingRoutes;
