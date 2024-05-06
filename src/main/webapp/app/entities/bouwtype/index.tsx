import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Bouwtype from './bouwtype';
import BouwtypeDetail from './bouwtype-detail';
import BouwtypeUpdate from './bouwtype-update';
import BouwtypeDeleteDialog from './bouwtype-delete-dialog';

const BouwtypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Bouwtype />} />
    <Route path="new" element={<BouwtypeUpdate />} />
    <Route path=":id">
      <Route index element={<BouwtypeDetail />} />
      <Route path="edit" element={<BouwtypeUpdate />} />
      <Route path="delete" element={<BouwtypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BouwtypeRoutes;
