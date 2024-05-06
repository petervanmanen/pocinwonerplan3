import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Aanbesteding from './aanbesteding';
import AanbestedingDetail from './aanbesteding-detail';
import AanbestedingUpdate from './aanbesteding-update';
import AanbestedingDeleteDialog from './aanbesteding-delete-dialog';

const AanbestedingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Aanbesteding />} />
    <Route path="new" element={<AanbestedingUpdate />} />
    <Route path=":id">
      <Route index element={<AanbestedingDetail />} />
      <Route path="edit" element={<AanbestedingUpdate />} />
      <Route path="delete" element={<AanbestedingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AanbestedingRoutes;
