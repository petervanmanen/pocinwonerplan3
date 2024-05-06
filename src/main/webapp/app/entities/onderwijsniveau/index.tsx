import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Onderwijsniveau from './onderwijsniveau';
import OnderwijsniveauDetail from './onderwijsniveau-detail';
import OnderwijsniveauUpdate from './onderwijsniveau-update';
import OnderwijsniveauDeleteDialog from './onderwijsniveau-delete-dialog';

const OnderwijsniveauRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Onderwijsniveau />} />
    <Route path="new" element={<OnderwijsniveauUpdate />} />
    <Route path=":id">
      <Route index element={<OnderwijsniveauDetail />} />
      <Route path="edit" element={<OnderwijsniveauUpdate />} />
      <Route path="delete" element={<OnderwijsniveauDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OnderwijsniveauRoutes;
