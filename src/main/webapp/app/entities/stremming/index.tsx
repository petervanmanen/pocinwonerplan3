import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Stremming from './stremming';
import StremmingDetail from './stremming-detail';
import StremmingUpdate from './stremming-update';
import StremmingDeleteDialog from './stremming-delete-dialog';

const StremmingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Stremming />} />
    <Route path="new" element={<StremmingUpdate />} />
    <Route path=":id">
      <Route index element={<StremmingDetail />} />
      <Route path="edit" element={<StremmingUpdate />} />
      <Route path="delete" element={<StremmingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StremmingRoutes;
