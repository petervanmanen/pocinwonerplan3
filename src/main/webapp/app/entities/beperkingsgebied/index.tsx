import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Beperkingsgebied from './beperkingsgebied';
import BeperkingsgebiedDetail from './beperkingsgebied-detail';
import BeperkingsgebiedUpdate from './beperkingsgebied-update';
import BeperkingsgebiedDeleteDialog from './beperkingsgebied-delete-dialog';

const BeperkingsgebiedRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Beperkingsgebied />} />
    <Route path="new" element={<BeperkingsgebiedUpdate />} />
    <Route path=":id">
      <Route index element={<BeperkingsgebiedDetail />} />
      <Route path="edit" element={<BeperkingsgebiedUpdate />} />
      <Route path="delete" element={<BeperkingsgebiedDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BeperkingsgebiedRoutes;
