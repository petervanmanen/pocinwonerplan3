import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Klimplant from './klimplant';
import KlimplantDetail from './klimplant-detail';
import KlimplantUpdate from './klimplant-update';
import KlimplantDeleteDialog from './klimplant-delete-dialog';

const KlimplantRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Klimplant />} />
    <Route path="new" element={<KlimplantUpdate />} />
    <Route path=":id">
      <Route index element={<KlimplantDetail />} />
      <Route path="edit" element={<KlimplantUpdate />} />
      <Route path="delete" element={<KlimplantDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KlimplantRoutes;
