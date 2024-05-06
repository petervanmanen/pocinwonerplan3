import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Kwaliteitskenmerken from './kwaliteitskenmerken';
import KwaliteitskenmerkenDetail from './kwaliteitskenmerken-detail';
import KwaliteitskenmerkenUpdate from './kwaliteitskenmerken-update';
import KwaliteitskenmerkenDeleteDialog from './kwaliteitskenmerken-delete-dialog';

const KwaliteitskenmerkenRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Kwaliteitskenmerken />} />
    <Route path="new" element={<KwaliteitskenmerkenUpdate />} />
    <Route path=":id">
      <Route index element={<KwaliteitskenmerkenDetail />} />
      <Route path="edit" element={<KwaliteitskenmerkenUpdate />} />
      <Route path="delete" element={<KwaliteitskenmerkenDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KwaliteitskenmerkenRoutes;
