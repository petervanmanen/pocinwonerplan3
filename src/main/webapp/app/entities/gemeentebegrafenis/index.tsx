import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Gemeentebegrafenis from './gemeentebegrafenis';
import GemeentebegrafenisDetail from './gemeentebegrafenis-detail';
import GemeentebegrafenisUpdate from './gemeentebegrafenis-update';
import GemeentebegrafenisDeleteDialog from './gemeentebegrafenis-delete-dialog';

const GemeentebegrafenisRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Gemeentebegrafenis />} />
    <Route path="new" element={<GemeentebegrafenisUpdate />} />
    <Route path=":id">
      <Route index element={<GemeentebegrafenisDetail />} />
      <Route path="edit" element={<GemeentebegrafenisUpdate />} />
      <Route path="delete" element={<GemeentebegrafenisDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GemeentebegrafenisRoutes;
