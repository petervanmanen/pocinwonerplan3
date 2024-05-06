import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Software from './software';
import SoftwareDetail from './software-detail';
import SoftwareUpdate from './software-update';
import SoftwareDeleteDialog from './software-delete-dialog';

const SoftwareRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Software />} />
    <Route path="new" element={<SoftwareUpdate />} />
    <Route path=":id">
      <Route index element={<SoftwareDetail />} />
      <Route path="edit" element={<SoftwareUpdate />} />
      <Route path="delete" element={<SoftwareDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SoftwareRoutes;
