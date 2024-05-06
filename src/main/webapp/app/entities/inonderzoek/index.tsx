import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Inonderzoek from './inonderzoek';
import InonderzoekDetail from './inonderzoek-detail';
import InonderzoekUpdate from './inonderzoek-update';
import InonderzoekDeleteDialog from './inonderzoek-delete-dialog';

const InonderzoekRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Inonderzoek />} />
    <Route path="new" element={<InonderzoekUpdate />} />
    <Route path=":id">
      <Route index element={<InonderzoekDetail />} />
      <Route path="edit" element={<InonderzoekUpdate />} />
      <Route path="delete" element={<InonderzoekDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InonderzoekRoutes;
