import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Conclusie from './conclusie';
import ConclusieDetail from './conclusie-detail';
import ConclusieUpdate from './conclusie-update';
import ConclusieDeleteDialog from './conclusie-delete-dialog';

const ConclusieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Conclusie />} />
    <Route path="new" element={<ConclusieUpdate />} />
    <Route path=":id">
      <Route index element={<ConclusieDetail />} />
      <Route path="edit" element={<ConclusieUpdate />} />
      <Route path="delete" element={<ConclusieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ConclusieRoutes;
