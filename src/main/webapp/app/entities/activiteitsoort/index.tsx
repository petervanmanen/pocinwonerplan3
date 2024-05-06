import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Activiteitsoort from './activiteitsoort';
import ActiviteitsoortDetail from './activiteitsoort-detail';
import ActiviteitsoortUpdate from './activiteitsoort-update';
import ActiviteitsoortDeleteDialog from './activiteitsoort-delete-dialog';

const ActiviteitsoortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Activiteitsoort />} />
    <Route path="new" element={<ActiviteitsoortUpdate />} />
    <Route path=":id">
      <Route index element={<ActiviteitsoortDetail />} />
      <Route path="edit" element={<ActiviteitsoortUpdate />} />
      <Route path="delete" element={<ActiviteitsoortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ActiviteitsoortRoutes;
