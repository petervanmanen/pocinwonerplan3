import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Terreindeel from './terreindeel';
import TerreindeelDetail from './terreindeel-detail';
import TerreindeelUpdate from './terreindeel-update';
import TerreindeelDeleteDialog from './terreindeel-delete-dialog';

const TerreindeelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Terreindeel />} />
    <Route path="new" element={<TerreindeelUpdate />} />
    <Route path=":id">
      <Route index element={<TerreindeelDetail />} />
      <Route path="edit" element={<TerreindeelUpdate />} />
      <Route path="delete" element={<TerreindeelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TerreindeelRoutes;
