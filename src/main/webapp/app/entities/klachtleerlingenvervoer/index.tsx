import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Klachtleerlingenvervoer from './klachtleerlingenvervoer';
import KlachtleerlingenvervoerDetail from './klachtleerlingenvervoer-detail';
import KlachtleerlingenvervoerUpdate from './klachtleerlingenvervoer-update';
import KlachtleerlingenvervoerDeleteDialog from './klachtleerlingenvervoer-delete-dialog';

const KlachtleerlingenvervoerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Klachtleerlingenvervoer />} />
    <Route path="new" element={<KlachtleerlingenvervoerUpdate />} />
    <Route path=":id">
      <Route index element={<KlachtleerlingenvervoerDetail />} />
      <Route path="edit" element={<KlachtleerlingenvervoerUpdate />} />
      <Route path="delete" element={<KlachtleerlingenvervoerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KlachtleerlingenvervoerRoutes;
