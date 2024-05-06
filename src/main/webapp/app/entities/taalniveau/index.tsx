import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Taalniveau from './taalniveau';
import TaalniveauDetail from './taalniveau-detail';
import TaalniveauUpdate from './taalniveau-update';
import TaalniveauDeleteDialog from './taalniveau-delete-dialog';

const TaalniveauRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Taalniveau />} />
    <Route path="new" element={<TaalniveauUpdate />} />
    <Route path=":id">
      <Route index element={<TaalniveauDetail />} />
      <Route path="edit" element={<TaalniveauUpdate />} />
      <Route path="delete" element={<TaalniveauDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TaalniveauRoutes;
