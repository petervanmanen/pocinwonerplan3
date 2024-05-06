import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Aanvraagleerlingenvervoer from './aanvraagleerlingenvervoer';
import AanvraagleerlingenvervoerDetail from './aanvraagleerlingenvervoer-detail';
import AanvraagleerlingenvervoerUpdate from './aanvraagleerlingenvervoer-update';
import AanvraagleerlingenvervoerDeleteDialog from './aanvraagleerlingenvervoer-delete-dialog';

const AanvraagleerlingenvervoerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Aanvraagleerlingenvervoer />} />
    <Route path="new" element={<AanvraagleerlingenvervoerUpdate />} />
    <Route path=":id">
      <Route index element={<AanvraagleerlingenvervoerDetail />} />
      <Route path="edit" element={<AanvraagleerlingenvervoerUpdate />} />
      <Route path="delete" element={<AanvraagleerlingenvervoerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AanvraagleerlingenvervoerRoutes;
