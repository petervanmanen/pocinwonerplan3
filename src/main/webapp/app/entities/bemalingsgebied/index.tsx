import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Bemalingsgebied from './bemalingsgebied';
import BemalingsgebiedDetail from './bemalingsgebied-detail';
import BemalingsgebiedUpdate from './bemalingsgebied-update';
import BemalingsgebiedDeleteDialog from './bemalingsgebied-delete-dialog';

const BemalingsgebiedRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Bemalingsgebied />} />
    <Route path="new" element={<BemalingsgebiedUpdate />} />
    <Route path=":id">
      <Route index element={<BemalingsgebiedDetail />} />
      <Route path="edit" element={<BemalingsgebiedUpdate />} />
      <Route path="delete" element={<BemalingsgebiedDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BemalingsgebiedRoutes;
