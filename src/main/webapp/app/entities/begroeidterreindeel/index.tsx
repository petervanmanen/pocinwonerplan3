import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Begroeidterreindeel from './begroeidterreindeel';
import BegroeidterreindeelDetail from './begroeidterreindeel-detail';
import BegroeidterreindeelUpdate from './begroeidterreindeel-update';
import BegroeidterreindeelDeleteDialog from './begroeidterreindeel-delete-dialog';

const BegroeidterreindeelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Begroeidterreindeel />} />
    <Route path="new" element={<BegroeidterreindeelUpdate />} />
    <Route path=":id">
      <Route index element={<BegroeidterreindeelDetail />} />
      <Route path="edit" element={<BegroeidterreindeelUpdate />} />
      <Route path="delete" element={<BegroeidterreindeelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BegroeidterreindeelRoutes;
