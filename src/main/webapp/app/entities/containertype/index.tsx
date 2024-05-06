import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Containertype from './containertype';
import ContainertypeDetail from './containertype-detail';
import ContainertypeUpdate from './containertype-update';
import ContainertypeDeleteDialog from './containertype-delete-dialog';

const ContainertypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Containertype />} />
    <Route path="new" element={<ContainertypeUpdate />} />
    <Route path=":id">
      <Route index element={<ContainertypeDetail />} />
      <Route path="edit" element={<ContainertypeUpdate />} />
      <Route path="delete" element={<ContainertypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ContainertypeRoutes;
