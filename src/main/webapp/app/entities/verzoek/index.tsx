import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Verzoek from './verzoek';
import VerzoekDetail from './verzoek-detail';
import VerzoekUpdate from './verzoek-update';
import VerzoekDeleteDialog from './verzoek-delete-dialog';

const VerzoekRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Verzoek />} />
    <Route path="new" element={<VerzoekUpdate />} />
    <Route path=":id">
      <Route index element={<VerzoekDetail />} />
      <Route path="edit" element={<VerzoekUpdate />} />
      <Route path="delete" element={<VerzoekDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default VerzoekRoutes;
