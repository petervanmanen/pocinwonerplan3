import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Kpclassaclassc from './kpclassaclassc';
import KpclassaclasscDetail from './kpclassaclassc-detail';
import KpclassaclasscUpdate from './kpclassaclassc-update';
import KpclassaclasscDeleteDialog from './kpclassaclassc-delete-dialog';

const KpclassaclasscRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Kpclassaclassc />} />
    <Route path="new" element={<KpclassaclasscUpdate />} />
    <Route path=":id">
      <Route index element={<KpclassaclasscDetail />} />
      <Route path="edit" element={<KpclassaclasscUpdate />} />
      <Route path="delete" element={<KpclassaclasscDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KpclassaclasscRoutes;
