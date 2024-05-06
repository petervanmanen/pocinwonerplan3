import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Beperkingscoresoort from './beperkingscoresoort';
import BeperkingscoresoortDetail from './beperkingscoresoort-detail';
import BeperkingscoresoortUpdate from './beperkingscoresoort-update';
import BeperkingscoresoortDeleteDialog from './beperkingscoresoort-delete-dialog';

const BeperkingscoresoortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Beperkingscoresoort />} />
    <Route path="new" element={<BeperkingscoresoortUpdate />} />
    <Route path=":id">
      <Route index element={<BeperkingscoresoortDetail />} />
      <Route path="edit" element={<BeperkingscoresoortUpdate />} />
      <Route path="delete" element={<BeperkingscoresoortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BeperkingscoresoortRoutes;
