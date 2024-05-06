import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Zaaktype from './zaaktype';
import ZaaktypeDetail from './zaaktype-detail';
import ZaaktypeUpdate from './zaaktype-update';
import ZaaktypeDeleteDialog from './zaaktype-delete-dialog';

const ZaaktypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Zaaktype />} />
    <Route path="new" element={<ZaaktypeUpdate />} />
    <Route path=":id">
      <Route index element={<ZaaktypeDetail />} />
      <Route path="edit" element={<ZaaktypeUpdate />} />
      <Route path="delete" element={<ZaaktypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ZaaktypeRoutes;
