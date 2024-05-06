import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Magazijnplaatsing from './magazijnplaatsing';
import MagazijnplaatsingDetail from './magazijnplaatsing-detail';
import MagazijnplaatsingUpdate from './magazijnplaatsing-update';
import MagazijnplaatsingDeleteDialog from './magazijnplaatsing-delete-dialog';

const MagazijnplaatsingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Magazijnplaatsing />} />
    <Route path="new" element={<MagazijnplaatsingUpdate />} />
    <Route path=":id">
      <Route index element={<MagazijnplaatsingDetail />} />
      <Route path="edit" element={<MagazijnplaatsingUpdate />} />
      <Route path="delete" element={<MagazijnplaatsingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MagazijnplaatsingRoutes;
