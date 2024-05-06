import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Zaakorigineel from './zaakorigineel';
import ZaakorigineelDetail from './zaakorigineel-detail';
import ZaakorigineelUpdate from './zaakorigineel-update';
import ZaakorigineelDeleteDialog from './zaakorigineel-delete-dialog';

const ZaakorigineelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Zaakorigineel />} />
    <Route path="new" element={<ZaakorigineelUpdate />} />
    <Route path=":id">
      <Route index element={<ZaakorigineelDetail />} />
      <Route path="edit" element={<ZaakorigineelUpdate />} />
      <Route path="delete" element={<ZaakorigineelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ZaakorigineelRoutes;
