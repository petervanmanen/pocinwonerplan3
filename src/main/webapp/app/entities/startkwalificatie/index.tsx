import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Startkwalificatie from './startkwalificatie';
import StartkwalificatieDetail from './startkwalificatie-detail';
import StartkwalificatieUpdate from './startkwalificatie-update';
import StartkwalificatieDeleteDialog from './startkwalificatie-delete-dialog';

const StartkwalificatieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Startkwalificatie />} />
    <Route path="new" element={<StartkwalificatieUpdate />} />
    <Route path=":id">
      <Route index element={<StartkwalificatieDetail />} />
      <Route path="edit" element={<StartkwalificatieUpdate />} />
      <Route path="delete" element={<StartkwalificatieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StartkwalificatieRoutes;
