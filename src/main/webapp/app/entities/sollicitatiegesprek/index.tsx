import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Sollicitatiegesprek from './sollicitatiegesprek';
import SollicitatiegesprekDetail from './sollicitatiegesprek-detail';
import SollicitatiegesprekUpdate from './sollicitatiegesprek-update';
import SollicitatiegesprekDeleteDialog from './sollicitatiegesprek-delete-dialog';

const SollicitatiegesprekRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Sollicitatiegesprek />} />
    <Route path="new" element={<SollicitatiegesprekUpdate />} />
    <Route path=":id">
      <Route index element={<SollicitatiegesprekDetail />} />
      <Route path="edit" element={<SollicitatiegesprekUpdate />} />
      <Route path="delete" element={<SollicitatiegesprekDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SollicitatiegesprekRoutes;
