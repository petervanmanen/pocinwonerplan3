import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Leefgebied from './leefgebied';
import LeefgebiedDetail from './leefgebied-detail';
import LeefgebiedUpdate from './leefgebied-update';
import LeefgebiedDeleteDialog from './leefgebied-delete-dialog';

const LeefgebiedRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Leefgebied />} />
    <Route path="new" element={<LeefgebiedUpdate />} />
    <Route path=":id">
      <Route index element={<LeefgebiedDetail />} />
      <Route path="edit" element={<LeefgebiedUpdate />} />
      <Route path="delete" element={<LeefgebiedDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LeefgebiedRoutes;
