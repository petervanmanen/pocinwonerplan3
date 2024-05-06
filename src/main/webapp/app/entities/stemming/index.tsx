import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Stemming from './stemming';
import StemmingDetail from './stemming-detail';
import StemmingUpdate from './stemming-update';
import StemmingDeleteDialog from './stemming-delete-dialog';

const StemmingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Stemming />} />
    <Route path="new" element={<StemmingUpdate />} />
    <Route path=":id">
      <Route index element={<StemmingDetail />} />
      <Route path="edit" element={<StemmingUpdate />} />
      <Route path="delete" element={<StemmingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StemmingRoutes;
