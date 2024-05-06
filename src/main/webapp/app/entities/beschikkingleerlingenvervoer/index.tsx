import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Beschikkingleerlingenvervoer from './beschikkingleerlingenvervoer';
import BeschikkingleerlingenvervoerDetail from './beschikkingleerlingenvervoer-detail';
import BeschikkingleerlingenvervoerUpdate from './beschikkingleerlingenvervoer-update';
import BeschikkingleerlingenvervoerDeleteDialog from './beschikkingleerlingenvervoer-delete-dialog';

const BeschikkingleerlingenvervoerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Beschikkingleerlingenvervoer />} />
    <Route path="new" element={<BeschikkingleerlingenvervoerUpdate />} />
    <Route path=":id">
      <Route index element={<BeschikkingleerlingenvervoerDetail />} />
      <Route path="edit" element={<BeschikkingleerlingenvervoerUpdate />} />
      <Route path="delete" element={<BeschikkingleerlingenvervoerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BeschikkingleerlingenvervoerRoutes;
