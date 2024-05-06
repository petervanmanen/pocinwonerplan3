import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Onderhoud from './onderhoud';
import OnderhoudDetail from './onderhoud-detail';
import OnderhoudUpdate from './onderhoud-update';
import OnderhoudDeleteDialog from './onderhoud-delete-dialog';

const OnderhoudRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Onderhoud />} />
    <Route path="new" element={<OnderhoudUpdate />} />
    <Route path=":id">
      <Route index element={<OnderhoudDetail />} />
      <Route path="edit" element={<OnderhoudUpdate />} />
      <Route path="delete" element={<OnderhoudDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OnderhoudRoutes;
