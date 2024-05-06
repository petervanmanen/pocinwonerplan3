import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Normprofiel from './normprofiel';
import NormprofielDetail from './normprofiel-detail';
import NormprofielUpdate from './normprofiel-update';
import NormprofielDeleteDialog from './normprofiel-delete-dialog';

const NormprofielRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Normprofiel />} />
    <Route path="new" element={<NormprofielUpdate />} />
    <Route path=":id">
      <Route index element={<NormprofielDetail />} />
      <Route path="edit" element={<NormprofielUpdate />} />
      <Route path="delete" element={<NormprofielDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NormprofielRoutes;
