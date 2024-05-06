import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Kandidaat from './kandidaat';
import KandidaatDetail from './kandidaat-detail';
import KandidaatUpdate from './kandidaat-update';
import KandidaatDeleteDialog from './kandidaat-delete-dialog';

const KandidaatRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Kandidaat />} />
    <Route path="new" element={<KandidaatUpdate />} />
    <Route path=":id">
      <Route index element={<KandidaatDetail />} />
      <Route path="edit" element={<KandidaatUpdate />} />
      <Route path="delete" element={<KandidaatDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KandidaatRoutes;
