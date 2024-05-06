import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Aanbestedingvastgoed from './aanbestedingvastgoed';
import AanbestedingvastgoedDetail from './aanbestedingvastgoed-detail';
import AanbestedingvastgoedUpdate from './aanbestedingvastgoed-update';
import AanbestedingvastgoedDeleteDialog from './aanbestedingvastgoed-delete-dialog';

const AanbestedingvastgoedRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Aanbestedingvastgoed />} />
    <Route path="new" element={<AanbestedingvastgoedUpdate />} />
    <Route path=":id">
      <Route index element={<AanbestedingvastgoedDetail />} />
      <Route path="edit" element={<AanbestedingvastgoedUpdate />} />
      <Route path="delete" element={<AanbestedingvastgoedDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AanbestedingvastgoedRoutes;
