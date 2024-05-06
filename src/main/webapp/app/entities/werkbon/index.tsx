import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Werkbon from './werkbon';
import WerkbonDetail from './werkbon-detail';
import WerkbonUpdate from './werkbon-update';
import WerkbonDeleteDialog from './werkbon-delete-dialog';

const WerkbonRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Werkbon />} />
    <Route path="new" element={<WerkbonUpdate />} />
    <Route path=":id">
      <Route index element={<WerkbonDetail />} />
      <Route path="edit" element={<WerkbonUpdate />} />
      <Route path="delete" element={<WerkbonDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default WerkbonRoutes;
