import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Rechtspersoon from './rechtspersoon';
import RechtspersoonDetail from './rechtspersoon-detail';
import RechtspersoonUpdate from './rechtspersoon-update';
import RechtspersoonDeleteDialog from './rechtspersoon-delete-dialog';

const RechtspersoonRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Rechtspersoon />} />
    <Route path="new" element={<RechtspersoonUpdate />} />
    <Route path=":id">
      <Route index element={<RechtspersoonDetail />} />
      <Route path="edit" element={<RechtspersoonUpdate />} />
      <Route path="delete" element={<RechtspersoonDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RechtspersoonRoutes;
